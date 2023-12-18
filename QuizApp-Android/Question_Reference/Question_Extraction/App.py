import MySQLdb
import flask
from flask import Flask, render_template, request, redirect
import mysql.connector
from flask_mysqldb import MySQL
from datetime import datetime
from flask import url_for
from Question_reference import SlideWindow


app = Flask(__name__)
mysql = MySQL(app)

myDb = mysql.connector.connect(
    host="localhost",
    user="root",
    password="Brokenvessels27",
    port="3306",
    database="b'its'"
)

cursor = myDb.cursor()


@app.route('/choose_questions', methods=['GET', 'POST'])
def choose_questions():
    try:
        if request.method == 'POST':
            # fetch form data
            if request.form['submit_button'] == '1':
                query = "select question from questions where status = 'publish'  and category = 'Chapter1' ORDER by RAND() LIMIT 1"
                return redirect(url_for('display_questions', query=query))
            elif request.form['submit_button'] == '2':
                query = "select question from questions where status = 'publish'  and category = 'Chapter2' ORDER by RAND() LIMIT 1"
                return redirect(url_for('display_questions', query=query))
            elif request.form['submit_button'] == '3':
                query = "select question from questions where status = 'publish'  and category = 'Chapter3' ORDER by RAND() LIMIT 1"
                return redirect(url_for('display_questions', query=query))
            elif request.form['submit_button'] == '4':
                query = "select question from questions where status = 'publish'  and category = 'Chapter4' ORDER by RAND() LIMIT 1"
                return redirect(url_for('display_questions', query=query))
            elif request.form['submit_button'] == '5':
                query = "select question from questions where status = 'publish'  and category = 'Chapter5' ORDER by RAND() LIMIT 1"
                return redirect(url_for('display_questions', query=query))
            elif request.form['submit_button'] == '6':
                query = "select question from questions where status = 'publish'  and category = 'Chapter6' ORDER by RAND() LIMIT 1"
                return redirect(url_for('display_questions', query=query))
            elif request.form['submit_button'] == '7':
                query = "select question from questions where status = 'publish'  and category = 'Chapter7' ORDER by RAND() LIMIT 1"
                return redirect(url_for('display_questions', query=query))
            elif request.form['submit_button'] == '8':
                query = "select question from questions where status = 'publish'  and category = 'Chapter8' ORDER by RAND() LIMIT 1"
                return redirect(url_for('display_questions', query=query))
            elif request.form['submit_button'] == '9':
                query = "select question from questions where status = 'publish'  and category = 'Chapter9' ORDER by RAND() LIMIT 1"
                return redirect(url_for('display_questions', query=query))
            elif request.form['submit_button'] == '10':
                query = "select question from questions where status = 'publish'  and category = 'Chapter10' ORDER by RAND() LIMIT 1"
                return redirect(url_for('display_questions', query=query))
            elif request.form['submit_button'] == '11':
                query = "select question from questions where status = 'publish'  and category = 'Chapter11' ORDER by RAND() LIMIT 1"
                return redirect(url_for('display_questions', query=query))
            elif request.form['submit_button'] == '12':
                query = "select question from questions where status = 'publish'  and category = 'Chapter12' ORDER by RAND() LIMIT 1"
                return redirect(url_for('display_questions', query=query))
            
            
    except MySQLdb.Error as error:
            return "Failed to create due to this error: " + repr(error)

    return render_template('choose_questions.html')


@app.route('/display_questions', methods=['GET', 'POST'])
def display_questions(query):
    try:
        cursor.execute(query)
        res1 = cursor.fetchall()
        if res1 > 0:
            res = cursor.fetchall()
        else:
            res = []
        if request.method == 'POST':
            # fetch form data
            if request.form['submit_button'] == 'Back':
                # back to choosing topic page
                return redirect(url_for('choose_questions'))

            cursor.close()
    except MySQLdb.Error as error:
        return "Failed to create due to this error: " + repr(error)

    return render_template('display_questions.html', res=res)
    
if __name__ == '__main__':
    app.run(debug=True)