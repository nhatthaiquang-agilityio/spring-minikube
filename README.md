# Spring And Minikube
    Ingress Gateway: Simple fanout
    Student Service
    Rating Service


## Requirements
    Java: JDK 1.8
    Maven Build
    Docker
    Kubernetes: Minikube on local

## Usage

#### Build Student Service
```
$ cd rating-service
$ mvn package
```

#### Create docker images for Student Service and push Docker Hub
```
$ cd student-service
$ docker build -t nhatthai/student-service .
$ docker push nhatthai/student-service
```

#### Build Rating Service
```
$ cd rating-service
$ mvn package
```

#### Create docker images for Rating Service and push Docker Hub
```
$ cd rating-service
$ docker build -t nhatthai/rating-service .
$ docker push nhatthai/rating-service
```

#### Start Minikube
```
$ minikube start
```

#### Create student-service deployment
```
$ cd devops
$ kubectl create -f deployment-student.yml
```

#### Create rating-service deployment
```
$ cd devops
$ kubectl create -f deployment-rating.yml
```

#### Create and Expose student-service
```
$ kubectl expose deployment student-service --type=NodePort
```

or
```
$ cd devops
$ kubectl create -f service-student.yml
```

#### Create and Expose rating-service
```
$ kubectl expose deployment rating-service --type=NodePort
```

or
```
$ cd devops
$ kubectl create -f service-rating.yml
```


#### Check service
```
$ minikube service student-service --url
```
Give me url `http://192.168.99.100:30676` and Check rest-api http://192.168.99.100:30676/hi

```
$ kubectl describe service student-service
```

## Ingress Controller
Create Ingress or Basic Authentication Ingress

### Create Ingress
```
$ cd devops
$ kubectl create -f ingress.yml
```

### Using Basic Auth Ingress
Add user & pass when we reach the site.

#### Create htpasswd file
```
$ htpasswd -c auth example
New password: <bar>
New password:
Re-type new password:
Adding password for user example
```

#### Create secret
```
$ kubectl create secret generic basic-auth --from-file=auth
secret "basic-auth" created
```

#### Get Secert
Make sure that the auth secret was created

```
$ kubectl get secret basic-auth -o yaml
```

#### Create basic auth ingress
```
$ cd devops
$ kubectl create -f basic-auth-ingress.yml
```

#### Check Ingress
```
 curl -v http://192.168.99.100/student/hi -H 'Host: mysite.com'
*   Trying 192.168.99.100...
* TCP_NODELAY set
* Connected to 192.168.99.100 (192.168.99.100) port 80 (#0)
> GET /student/hi HTTP/1.1
> Host: mysite.com
> User-Agent: curl/7.54.0
> Accept: */*
>
< HTTP/1.1 401 Unauthorized
< Server: nginx/1.13.6
< Date: Tue, 27 Nov 2018 03:43:30 GMT
< Content-Type: text/html
< Content-Length: 195
< Connection: keep-alive
< WWW-Authenticate: Basic realm="Authentication Required - Example"
<
<html>
<head><title>401 Authorization Required</title></head>
<body bgcolor="white">
<center><h1>401 Authorization Required</h1></center>
<hr><center>nginx/1.13.6</center>
</body>
</html>
* Connection #0 to host 192.168.99.100 left intact
```

#### Add user & pass
```
curl -v http://192.168.99.100/student/hi -H 'Host: mysite.com' -u 'example:example'
*   Trying 192.168.99.100...
* TCP_NODELAY set
* Connected to 192.168.99.100 (192.168.99.100) port 80 (#0)
* Server auth using Basic with user 'example'
> GET /student/hi HTTP/1.1
> Host: mysite.com
> Authorization: Basic ZXhhbXBsZTpleGFtcGxl
> User-Agent: curl/7.54.0
> Accept: */*
>
< HTTP/1.1 200
< Server: nginx/1.13.6
< Date: Tue, 27 Nov 2018 03:44:38 GMT
< Content-Type: text/plain;charset=UTF-8
< Content-Length: 10
< Connection: keep-alive
< X-Application-Context: student-service:7000
<
* Connection #0 to host 192.168.99.100 left intact
Hi Student%
```

#### Check Ingress
```
$ kubectl describe ing
```
![Ingress](https://github.com/nhatthai/spring-minikube/blob/master/images/status-ingress.png "Ingress")

#### Enable Ingress
```
$ minikube addons enable ingress
```

```
$ minikube ip
192.168.99.100
```

#### Add mysite.com into /etc/hosts
```
192.168.99.100 mysite.com
```
Check browser: `http://mysite.com/student/hi`


## Reference
[Microservices with kubernetes and docker](https://piotrminkowski.wordpress.com/2017/03/31/microservices-with-kubernetes-and-docker/)

[Setting up Ingress on Minikube](https://medium.com/@Oskarr3/setting-up-ingress-on-minikube-6ae825e98f82)

[Basic Authentication Ingress](https://github.com/kubernetes/contrib/tree/master/ingress/controllers/nginx/examples/auth)

[Nginx Ingress Controller](http://rahmonov.me/posts/nginx-ingress-controller/)