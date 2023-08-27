# EECS 4413 Final Project

### Team JIL: Justin Law (217843145), Isaiah Linares (217287509), and Leah Radda (218324343)

### Source Code

- Link to our source code: https://github.com/Justinlaw1997/EECS_4413_Final_Project
- SQL scripts are in the root directory, in a file named **SQLScripts.txt**
- The code can be downloaded by clicking on the green Code button in the top right corner. The repostiory can either be cloned to your local machine by using the HTTPS link, or the zipfile can be downloaded directly to your computer.

### Deployment 

- Our site is deployed at the following URL: http://jil2.us-east-2.elasticbeanstalk.com/

### Running Locally

- Download/clone the files by following the instructions above
- Open with Eclipse and configure the following:
  - In ```Properties -> Project Facets```
    - Java should be set to Java 17
    - Dynamic Web Module should be set to 6.0
  - In ```Properties -> Java Build Path```
    - JRE System Library should be JavaSE-17
    - Server Runtime should be Tomcatv10.1
- Set up a new MySQL Workbench connection using the following credentials:
  - Hostname: awseb-e-bybgza4twa-stack-awsebrdsdatabase-57j2ooqi4tt8.cxocrl7z2rgw.us-east-2.rds.amazonaws.com
  - Username: root
  - Password: root1234
  - Port: 3306
- Start the server by right clicking on the project file and selecting ```Run As -> Run On Server```
- Once the server is running, navigate to http://localhost:8080/EECS4413FinalProjectJLI/
