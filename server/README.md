# SERVER

## MIE 2016430018 yssong

# This file show you how to open server and manage client.
1. How to open server
- build environment
- edit server port
- open server
2. How to manage client
- queue and cmd
- client list

---

1. How to open server
- build environment
>If your PC is on VMware, set 'bridge to adapter' at VMware network setting.
You should install python and additional modules.
```
($sudo apt-get upgrade)
($sudo apt-get update)
$ sudo apt-get install python
$ sudo apt-get install python-pip
$ pip install flask
$ pip install flask-restful
```

- edit server port
>If your port number 5000 is used now, edit your port number in last line of server.py.
Changed port number shoud be reflected to mobile application.

- open server
```
$ python server.py
```
>Server can be closed by keyboard interrupt.

---

2. How to manage client
- queue and cmd
>Client's message is sent to server in json format.
Json format is written at ./queue directory, when the message arrives.
After Json is written, server delivers json to queue manager.
Queue manager forwards json file sequentially to the data manager.
This json file has enum number to cmd element.

```
Manager cmd
50 : Camera monitoring
51 : Call client list(Ordered return recommended)
52 : Change client status
53 : Check message log
54 : Send message to client
55 : Check all visit log
```
```
Client cmd
0 : Enroll client to server
1 : Check whether you are registered(check also whether sent message)
2 : Send picture
3 : Check visit log
4 : Send message to manager
```

- client list
>Client_list/list.json is to manage the client's status.
Client is identifyed by phone number.
Each number has status number.
Status number refer to the rights of the customer.
```
0 : Unregistered
1 : Registered
2 : Manager
```
