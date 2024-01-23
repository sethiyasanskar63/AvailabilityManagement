Description

A resort named “Silver Heavens” has hundreds of bungalows. The guests can book bungalows and stay there to enjoy their
vacation.

The bungalows are grouped as per the number of adults who can stay in it. The resort has below types of bungalows:

- 2 person bungalow
- 4 person bungalow
- 6 person bungalow
- 8 person bungalow

So, for example, there can be 30 bungalows of type “2 person bungalow”, 50 bungalows of type “4 person bungalow” and so
on.
For better management, maintenance and efficient utilization of resources and manpower, the property management team
decides the periods for which bunglows can be booked. They make these decisions at bungalow type level.
To give you an example, below is a configuration for 2 person bungalow:

ID | STAY_DATE_FROM | STAY_DATE_TO | MIN_NIGHTS | ARRIVAL_DAYS | DEPARTURE_DAYS | BUNGALOW_TYPE
1 | 01 Apr 2024 | 30 Apr 2024 | 3 | Mon, Fri | Mon, Fri | 2 person bungalow
2 | 01 Jun 2024 | 30 Jun 2024 | 7 | All days | All days | 2 person bungalow

ID : The identifier
STAY_DATE_FROM / STAY_DATE_TO : The date range (period) in which the guest can arrive / depart.
MIN_NIGHTS: The minimum number of nights for which the guest should book the bungalow
ARRIVAL_DAYS: The days on which the guest can check-in.
DEPARTURE_DAYS: The days on which the guest can check-out.
BUNGALOW_TYPE: The type of the bungalow for which this availability is applicable

The first record from the below table says that – for 2 person bungalow, the guest can arrive (check-in) on any date
between 01 Apr to 30 Apr (both inclusive) and can depart (check-out) on any date between 01 Apr to 30 Apr, the guest has
to stay for minimum number of 3 nights, the guest can arrive only on Mondays and Fridays and depart only on Mondays and
Fridays.

The availability data can also be contiguous data. For example:

ID | STAY_DATE_FROM | STAY_DATE_TO | MIN_NIGHTS | ARRIVAL_DAYS | DEPARTURE_DAYS | BUNGALOW_TYPE
1 | 01 Apr 2024 | 30 Apr 2024 | 3 | Mon, Fri | Mon, Fri | 2 person bungalow
2 | 01 Jun 2024 | 30 Jun 2024 | 7 | All days | All days | 2 person bungalow

You can combine this availability data to decide when a guest can check-in and check-out. For example, here the guest
can check-in on any Monday / Friday that falls between 1 Apr to 30 Apr, stay for 7 nights and check-out on any day
between 1 May to 31 May.
As a backend developer, your job is to design, develop and unit test the endpoints for managing these availability, for
performing below actions:

1. Get all availability data
2. Get availability for given ID
3. Get all availability data for given bungalow type
4. Create a new availability data
5. Update an existing availability data
6. Delete an existing availability data
7. Get the list of types of bungalows available for given arrival date and departure date
8. Get the list of all possible arrival and departure dates for given bungalow type for next one year
9. Import availability data from an excel sheet into database
10. Export availability data from database to an excel sheet
