# selenium.user.test
a sample of a selenium script logging in and doing some stuff.


## Config

### images.txt
Copy paste the path towards a list of images, each on a new line.
D:\_Sharalike\Pictures\1.jpg
D:\_Sharalike\Pictures\2.jpg
D:\_Sharalike\Pictures\3.jpg
D:\_Sharalike\Pictures\4.jpg
D:\_Sharalike\Pictures\5.jpg
D:\_Sharalike\Pictures\6.jpg
D:\_Sharalike\Pictures\7.jpg
D:\_Sharalike\Pictures\8.jpg
D:\_Sharalike\Pictures\9.jpg


### config.properties
Target server
target.server = https://pre.sharalike.com
ChromeDriver location for selenium to use
web.driver.location =D:\\_Sharalike\\chromedriver.exe



## Usage

Maven run: 
mvn clean install exec:java -Dexec.args="1"

The argument represents the number of serial users.

Windows bat files:

## 1.

run.bat 10 

where 10 represents the number of serial  users.

## 2.
parallel-run.bat 2 3 

where 2 represents the number of parallel users and 3 the number of serial  users.


