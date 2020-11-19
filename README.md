# Java Desktop Scheduler
You are working for a software company that has been contracted to develop a scheduling desktop user interface application. The contract is with a global consulting organization that conducts business in multiple languages and has main offices in Phoenix, Arizona; New York, New York; and London, England. The consulting organization has provided a MySQL database that your application must pull data from. The database is used for other systems and therefore its structure cannot be modified.

## Requirements
1. Create a log-in form that can determine the user’s location and translate log-in and error control messages (e.g., “The username and password did not match.”) into two languages.
2. Provide the ability to enter and maintain customer records in the database, including name, address, and phone number.
3. Write lambda expression(s) to schedule and maintain appointments, capturing the type of appointment and a link to the specific customer record in the database.
4. Provide the ability to view the calendar by month and by week.
5. Provide the ability to automatically adjust appointment times based on user time zones and daylight saving time.
6. Write exception controls to prevent each of the following. You may use the same mechanism of exception control more than once, but you must incorporate at least two different mechanisms of exception control.
    - scheduling an appointment outside business hours
    - scheduling overlapping appointments
    - entering nonexistent or invalid customer data
    - entering an incorrect username and password
7. Use lambda expressions to create standard pop-up and alert messages.
8. Write code to provide reminders and alerts 15 minutes in advance of an appointment, based on the user’s log-in.
9. Provide the ability to generate each of the following reports:
    - number of appointment types by month
    - the schedule for each consultant
    - one additional report of your choice
10. Provide the ability to track user activity by recording timestamps for user log-ins in a .txt file. Each new record should be appended to the log file, if the file already exists.
