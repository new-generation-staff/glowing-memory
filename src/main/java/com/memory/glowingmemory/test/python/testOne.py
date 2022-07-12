import datetime
import json
import smtplib
from email.header import Header
from email.mime.text import MIMEText
from typing import Optional

import requests

print('test python one')


def string():
    str = '123456789'
    print(str)  # 输出字符串
    print(str[0:-1])  # 输出第一个到倒数第二个的所有字符
    print(str[0])  # 输出字符串第一个字符
    print(str[2:5])  # 输出从第三个开始到第五个的字符
    print(str[2:])  # 输出从第三个开始后的所有字符
    print(str[1:5:2])  # 输出从第二个开始到第五个且每隔一个的字符（步长为2）
    print(str * 2)  # 输出字符串两次
    print(str + '你好')  # 连接字符串

    print('------------------------------')

    print('hello\nrunoob')  # 使用反斜杠(\)+n转义特殊字符
    print(r'hello\nrunoob')  # 在字符串前面添加一个 r，表示原始字符串，不会发生转义
    # input("\n\n按下 enter 键后退出。")


# 列表(list)推导式
multiples = [i for i in range(30) if i % 3 == 0]
print(multiples)

# 字典(dict)推导式
dic = {x: x ** 2 for x in (2, 4, 6)}
print(dic)

# 集合(set)推导式
setNew = {i ** 2 for i in (1, 2, 3)}
print(setNew)


def func(a: int, b: Optional[int] = None):
    print(a)
    print(b)
    print(type(b))


# func(1)


def test():
    url = 'https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=f8321043-0bb6-486c-91f4-237f2d9181ac'
    header = {'Content-Type': 'application/json'}
    data = json.dumps({'msgtype': 'text', 'text': {'content': 'zc测试http请求'}})

    response = requests.post(url, headers=header, data=data)
    print(response.text)
    if response and response.text is not None:
        dictInfo = json.loads(response.text)
        print(dictInfo)
        print(dictInfo['errcode'])
        print(dictInfo['errmsg'])


# test()

def email_test():
    sender = 'from@runoob.com'
    receivers = ['huchen.zhao@convertlab.com']  # 接收邮件，可设置为你的QQ邮箱或者其他邮箱
    # 三个参数：第一个为文本内容，第二个 plain 设置文本格式，第三个 utf-8 设置编码
    message = MIMEText('邮件发送测试...', 'plain', 'utf-8')
    message['From'] = Header("菜鸟教程", 'utf-8')  # 发送者
    message['To'] = Header("测试", 'utf-8')  # 接收者

    subject = 'Python SMTP 邮件测试'
    message['Subject'] = Header(subject, 'utf-8')
    try:
        smtpObj = smtplib.SMTP('localhost')
        smtpObj.sendmail(sender, receivers, message.as_string())
        print("邮件发送成功")
    except smtplib.SMTPException:
        print("Error: 无法发送邮件")


# email_test()

def test2():
    args = '111'
    print(True if args else False)


test2()


def test3():
    var = datetime.datetime.now()
    print(var)
    print((var + datetime.timedelta(hours=8)).strftime('%Y-%m-%d %H:%M:%S'))


test3()
