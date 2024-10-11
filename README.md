
# Smart-ETL

Smart-ETL is a data extraction and transformation pipeline designed to automate the process of extracting data from car listing websites, transforming it according to predefined rules, and storing it for analysis. The project is built with modularity and flexibility in mind, allowing for easy customization and expansion.

## Features

- **Web Scraping**: Extract car listings from multiple websites using custom configurations.
- **Configurable**: Easily adjust the scraping rules and targets using the `car_sites_config.json` file.
- **Data Transformation**: Apply transformations such as formatting prices, extracting key information, and normalizing data.
- **Scalable**: Handle multiple pages and various listing formats dynamically.
- **Storage**: Store extracted data in a structured format for further analysis or integration with other systems.

## File Structure

- `app.py`: The main application file containing the logic for running the ETL process, including scraping and data transformation.
- `car_sites_config.json`: Configuration file that defines the structure and attributes of each target car listing website.
- `main.ipynb`: Jupyter Notebook used for prototyping and testing the ETL pipeline.
- `chat.java`: A file likely related to chatbot interaction or configuration (details based on project context).
- `job.sql`: SQL scripts that define the structure or operations for storing the extracted data in a database.

## Installation

To set up and run the Smart-ETL pipeline, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/cyrus1123/Smart-ETL.git
   ```

2. Navigate to the project directory:
   ```bash
   cd Smart-ETL
   ```

3. Install the required dependencies:
   ```bash
   pip install -r requirements.txt
   ```

## Usage

1. **Configure the ETL Pipeline**:
   - Edit the `car_sites_config.json` file to include the websites you want to scrape. This file allows you to define the structure of the web pages and the fields to extract.
   
   Example configuration for a website:
   ```json
   {
     "name": "ExampleCarSite1",
     "url_template": "https://examplecarsite1.com/used-cars?page={page}",
     "num_pages": 5,
     "listing_selector": "div.car-listing",
     "field_selectors": {
       "title": "h2.car-title",
       "price": "span.price",
       "year": "span.year",
       "mileage": "span.mileage",
       "location": "span.location"
     }
   }
   ```

2. **Run the ETL Process**:
   - Execute the main application script:
   ```bash
   python app.py
   ```

3. **Review and Store Data**:
   - The extracted and transformed data will be output in a structured format (e.g., CSV, JSON) or inserted into a database based on the configuration and `job.sql` script.

## Configuration Details

The `car_sites_config.json` file is crucial for defining how data is extracted from each website. It contains:

- **`name`**: The name of the car site.
- **`url_template`**: The URL pattern with placeholders for pagination.
- **`num_pages`**: Number of pages to scrape for the given website.
- **`listing_selector`**: CSS selector to identify individual listings on the page.
- **`field_selectors`**: CSS selectors for extracting fields like title, price, year, mileage, and location.

## Example Workflow

1. The application reads the configuration from `car_sites_config.json`.
2. It loops through each defined website, fetching and parsing the HTML pages using the specified selectors.
3. The extracted data is transformed based on rules and normalized for consistency.
4. The cleaned data is then saved for further analysis.

## Contributing

Contributions are welcome! If you wish to contribute to the Smart-ETL framework:

1. Fork the repository.
2. Create a new branch (`feature/your-feature`).
3. Make your changes and commit them.
4. Push your branch and create a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

## Contact

For questions or support, contact:

**Cyrus**  
GitHub: [cyrus1123](https://github.com/cyrus1123)
