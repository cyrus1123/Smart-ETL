from flask import Flask, render_template, request, jsonify, stream_with_context, Response
import requests
import json

app = Flask(__name__)

# Define the URL for the Ollama server
OLLAMA_URL = "http://localhost:11434/api/generate"

# Initialize chat history
chat_history = []

# Function to chat with Llama3.1 via Ollama
def chat_with_llama(text):
    # Add user input to chat history
    chat_history.append(f"User: {text}")
    # Combine chat history into a single string prompt
    full_chat_history = "\n".join(chat_history)

    # Prepare data to send to Ollama
    data = {
        "model": "llama3.1",
        "prompt": full_chat_history,
        "stream": True  # Enable streaming
    }

    # Streaming generator function
    def generate():
        try:
            with requests.post(OLLAMA_URL, json=data, stream=True) as response:
                if response.status_code == 200:
                    buffer = ""  # Buffer to hold streaming parts until a full sentence is formed
                    # Read the streaming response line by line
                    for line in response.iter_lines():
                        if line:
                            decoded_line = line.decode('utf-8')
                            try:
                                # Parse the JSON response
                                response_data = json.loads(decoded_line)
                                # Extract the 'response' part
                                response_message = response_data.get("response", "")
                                buffer += response_message
                                
                                # Print and yield a complete sentence
                                if buffer.endswith(('.', '!', '?')):
                                    yield f"data: {buffer.strip()}\n\n"
                                    chat_history.append(f"Bot: {buffer.strip()}")
                                    buffer = ""
                            except json.JSONDecodeError:
                                print(f"Error decoding line: {decoded_line}")
                    
                    # Yield any remaining buffer as final response
                    if buffer:
                        yield f"data: {buffer.strip()}\n\n"
                        chat_history.append(f"Bot: {buffer.strip()}")
                else:
                    yield f"data: Error: Failed with status code {response.status_code}. Message: {response.text}\n\n"
        except requests.exceptions.RequestException as e:
            yield f"data: Error communicating with Ollama: {e}\n\n"

    return generate()

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/chat', methods=['POST'])
def chat():
    user_input = request.form['user_input']
    # Stream response to the client
    return Response(stream_with_context(chat_with_llama(user_input)), mimetype='text/event-stream')

if __name__ == '__main__':
    app.run(debug=True)
