import mysql.connector

myDb = mysql.connector.connect(
    host="localhost",
    user="root",
    password="password",
    port="3306",
    database="its"
)

def query_Sql(a):
    cursor.execute(a)
    users = cursor.fetchall()
    for user in users:
        print(user[4])
        return(user[4])


cursor = myDb.cursor()

def querySql(prompt):
        if prompt == "Chapter 1":
            sql_select_query1 = "select * from questions where status = 'publish'  and category = 'Chapter1' LIMIT 1"
            return query_Sql(sql_select_query1)
        elif prompt == "Chapter 2":
            sql_select_query2 = "select * from questions where status = 'publish'  and category = 'Chapter2' ORDER by RAND() LIMIT 1"
            return query_Sql(sql_select_query2)
        elif prompt ==  "Chapter 3":
            sql_select_query3 = "select * from questions where status = 'publish'  and category = 'Chapter3' ORDER by RAND() LIMIT 1"
            return query_Sql(sql_select_query3)
        elif prompt ==  "Chapter 4":
            sql_select_query4 = "select * from questions where status = 'publish'  and category = 'Chapter4' ORDER by RAND() LIMIT 1"
            return query_Sql(sql_select_query4)
        elif prompt ==  "Chapter 5":
            sql_select_query5 = "select * from questions where status = 'publish'  and category = 'Chapter5' ORDER by RAND() LIMIT 1"
            return query_Sql(sql_select_query5)
        elif prompt ==  "Chapter 6":
            sql_select_query6 = "select * from questions where status = 'publish'  and category = 'Chapter6' ORDER by RAND() LIMIT 1"
            return query_Sql(sql_select_query6)
        elif prompt ==  "Chapter 7":
            sql_select_query7 = "select * from questions where status = 'publish'  and category = 'Chapter7' ORDER by RAND() LIMIT 1"
            return query_Sql(sql_select_query7)
        elif prompt ==  "Chapter 8":
            sql_select_query8 = "select * from questions where status = 'publish'  and category LIKE 'Chapter8%' ORDER by RAND() LIMIT 1"
            return query_Sql(sql_select_query8)
        elif prompt ==  "Chapter 9":
            sql_select_query9 = "select * from questions where status = 'publish'  and category LIKE 'Chapter9%' ORDER by RAND() LIMIT 1"
            return query_Sql(sql_select_query9)
        elif prompt ==  "Chapter 10":
            sql_select_query10 = "select * from questions where status = 'publish'  and category LIKE 'Chapter10%' ORDER by RAND() LIMIT 1"
            return query_Sql(sql_select_query10)
        elif prompt ==  "Chapter 11":
            sql_select_query11 = "select * from questions where status = 'publish'  and category LIKE 'Chapter11%' ORDER by RAND() LIMIT 1"
            return query_Sql(sql_select_query11)
        elif prompt ==  "Chapter 12":
            sql_select_query12 = "select * from questions where status = 'publish'  and category LIKE 'Chapter12%' ORDER by RAND() LIMIT 1"
            return query_Sql(sql_select_query12)
        else:
            print("Selection invalid")
