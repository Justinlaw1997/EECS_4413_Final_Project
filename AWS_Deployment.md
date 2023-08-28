### AWS Elastic Beanstalk Deployment

The deployment configuration looks complicated but you don't need to understand all the setting. I mostly followed the AWS tutorials and a couple videos, this one is a bit different but does similar config i think https://www.youtube.com/watch?v=lq_TenN4Ylk.


### preconfig step - create role (not really too confident how this works) might be better to search how to do this yourself
1. Creat a AWS Service role
2. add the following permision
![image](https://github.com/Justinlaw1997/EECS_4413_Final_Project/assets/67449916/1211d2bf-a0d2-47e9-ae35-1cc399196337)

### preconfig - create a vpc (i don't rember how i did this :\) i will try to update later

## Setting up AWS Beanstalk Environment
1. go to Iam Roles
2. Create a Role
#### page 1 - Configure Environment
1. Chose Web server environment
2. add a domain name to access deployment
3. Platform should be Tomcat, I reccomend Appache 10
4. Application code -> chose sample code for now
5. Chose single instance free teir

#### page 2 - COnfigure Service acesss
1. chose existing default aws-elasticbeanstalk role
2. Ec2 key pair can leave empty
3. EC2 Instance Profile - chose the aws service role you created

#### page 3 - Setting up networking, db, an dtags
1. chose the vpc you created
2. Activate public IP and chose some subnet
3. (optional) enable db and chose a subnmet - you could also do this afterwards, probably more safer
4. (optional) if you enable a db set you engine user and password
5. 

#### page 4 
1. i didn't change anything on this page
2. you fight have to configure security groups

#### page 5
1. create SERVER_PORT Environmetn property with value 3306
2. i don't think jbdc connection string is important

## Pre depoyment
1. look up the platform you chose and see what build path configs are needed
2. For us we chose tomcat V10.1 which means we needed jav se-17, and to change our tomcat version to V10.1
3. We also had to chagne java facets to match and change dynamic webn module to 6.0
4. not for upgrade from tomcat 9 - 10, you need to cahnge all imports from javax to jakarta

## Deploymnent
1. After configuration is done and the environment is loaded, export your project as a WAR and upload it to the environment you craeted
2. After you see your index page showing up, try to add a db, you can use the system properties to connect given autiomatically when deploy
3. Connecting to db outside of deployment takes some additonal steps......
4. You may need to restructre you code to fit amazons preference, most likely not though
