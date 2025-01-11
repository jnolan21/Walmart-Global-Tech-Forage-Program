import csv
import sqlite3


# Creating the database tables for "shipping_data_1.csv" and "shipping_data_2.csv"
def create_database_tables(cursor):
    cursor.execute("""
        CREATE TABLE IF NOT EXISTS shipping_data_0 (
                    origin_warehouse TEXT,
                    destination_store TEXT,
                    product TEXT,
                    on_time TEXT,
                    product_quantity INTEGER,
                    driver_identifier TEXT
        )
    """)
    cursor.execute("""
        CREATE TABLE IF NOT EXISTS shipping_data_1 (
                   shipment_identifier TEXT,
                   product TEXT,
                   on_time TEXT,
                   origin_warehouse TEXT,
                   destination_store TEXT
        )
    """)

# Function that inserts data from "shipping_data_0.csv" into the shipping_data_0 database
def read_and_insert_shipping_data_0(cursor):
    with open('data/shipping_data_0.csv', 'r') as file:
        reader = csv.reader(file)
        next(reader)
        for row in reader:
            origin_warehouse, destination_store, product, on_time, product_quantity, driver_identifier = row
            # Insert the data into the table for shipping_data_0
            cursor.execute("INSERT INTO shipping_data_0 (origin_warehouse, destination_store, product, on_time, product_quantity, driver_identifier) VALUES (?, ?, ?, ?, ?, ?)",
            (origin_warehouse, destination_store, product, on_time, product_quantity, driver_identifier))

# Function that inserts data from "shipping_data_1.csv" and "shipping_data_2.csv" and  into the shipping_data_1 database
def read_and_insert_shipping_data_1_2(cursor):
    with open('data/shipping_data_2.csv', 'r') as file:
        reader = csv.reader(file)
        next(reader)
        rows_shipping_data_2 = [row for row in reader]
    with open('data/shipping_data_1.csv', 'r') as file:
        reader = csv.reader(file)
        next(reader)
        for row in reader:
            shipment_identifier, product, on_time = row
            rows_match = [r for r in rows_shipping_data_2 if r[0] == shipment_identifier]
            if rows_match:
                origin_warehouse, destination_store, driver_identifier = rows_match[0][1], rows_match[0][2], rows_match[0][3]
                cursor.execute("INSERT INTO shipping_data_1 (shipment_identifier, product, on_time, origin_warehouse, destination_store) VALUES (?, ?, ?, ?, ?)",
                               (shipment_identifier, product, on_time, origin_warehouse, destination_store))

# Main module for the script
if __name__ == "__main__":
    connection = sqlite3.connect('shipment_databse.db')
    cursor = connection.cursor()
    create_database_tables(cursor)
    read_and_insert_shipping_data_0(cursor)
    read_and_insert_shipping_data_1_2(cursor)
    connection.commit()
    connection.close()