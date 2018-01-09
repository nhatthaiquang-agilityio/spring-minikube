# Spring And Minikube
    Ingress Gateway: Simple fanout
    Student Service
    Rating Service


# Usage

Create docker images for Student Service and push Docker Hub
```
$ cd student-service
$ docker build -t nhatthai/student-service .
$ docker push nhatthai/student-service
```

Create docker images for Rating Service and push Docker Hub
```
$ cd rating-service
$ docker build -t nhatthai/rating-service .
$ docker push nhatthai/rating-service
```

Start Minikube
```
$ minikube start
```


Create student-service deployment
```
$ cd devops
$ kubectl create -f deployment-student.yml
```


Create rating-service deployment
```
$ cd devops
$ kubectl create -f deployment-rating.yml
```


Create and Expose student-service
```
$ kubectl expose deployment student-service --type=NodePort
```


Create and Expose rating-service
```
$ kubectl expose deployment rating-service --type=NodePort
```


Check service
```
$ minikube service student-service --url
```
Give me url `http://192.168.99.100:30676` and Check rest-api http://192.168.99.100:30676/hi

```
$ kubectl describe service student-service
```


Create Ingress
```
$ cd devops
$ kubectl create -f ingress.yml
```


Check Ingress
```
$ kubectl describe ing
```
![Ingress](https://github.com/nhatthai/spring-minikube/blob/master/images/status-ingress.png "Ingress")

Add mysite.com into /etc/hosts
```
192.168.99.100 mysite.com
```
Check browser: `http://mysite.com/student/hi`


### Notes:
Enable Ingress
```
$ minikube addons enable ingress
```

### Reference
[Microservices with kubernetes and docker](https://piotrminkowski.wordpress.com/2017/03/31/microservices-with-kubernetes-and-docker/)
[Setting up Ingress on Minikube](https://medium.com/@Oskarr3/setting-up-ingress-on-minikube-6ae825e98f82)