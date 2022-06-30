import json
import os
import smtplib
from datetime import datetime, timedelta
from email.header import Header
from email.mime.text import MIMEText

import requests
from airflow import DAG
from airflow.configuration import conf

from airflow.operators.bash import BashOperator
from airflow.operators.email import EmailOperator
from airflow.operators.python import PythonOperator
from airflow.providers.ssh.hooks.ssh import SSHHook
from airflow.providers.ssh.operators.ssh import SSHOperator


def default_failure_callback(context):
    dag_id = context['dag'].dag_id
    task_id = context['ti'].task_id
    execution_date = context['ds']
    print("dag_id" + dag_id)
    print("task_id" + task_id)
    print("execution_date" + execution_date)
    try:
        print("default_failure_callback")
    except:
        pass


def http_test():
    url = ''
    header = {'Content-Type': 'application/json'}
    data = json.dumps({'msgtype': 'text', 'text': {'content': 'test http'}})

    response = requests.post(url, headers=header, data=data)
    if response and response.text is not None:
        dictInfo = json.loads(response.text)
        print(dictInfo)
        print(dictInfo['errcode'])
        print(dictInfo['errmsg'])


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


default_args = {
    'owner': 'airflow',
    'depends_on_past': False,
    'email': ['airflow@example.com'],
    'email_on_failure': False,
    'email_on_retry': False,
    'retries': 1,
    'retry_delay': timedelta(minutes=2),
    'on_failure_callback': default_failure_callback,
    # 'queue': 'bash_queue',
    # 'pool': 'backfill',
    # 'priority_weight': 10,
    # 'end_date': datetime(2016, 1, 1),
    # 'wait_for_downstream': False,
    # 'sla': timedelta(hours=2),
    # 'execution_timeout': timedelta(seconds=300),
    # 'on_failure_callback': default_failure_callback,
    # 'on_success_callback': some_other_function,
    # 'on_retry_callback': another_function,
    # 'sla_miss_callback': yet_another_function,
    # 'trigger_rule': 'all_success'
}

with DAG(
        dag_id='uow_submit_spark_job',
        default_args=default_args,
        description='UOW submit spark job',
        schedule_interval=None,
        start_date=datetime(2022, 1, 1),
        catchup=False,
        tags=['uow'],
) as dag:
    dag.doc_md = '''
    This DAG is uow_submit_spark_job
    '''

    t1 = BashOperator(
        task_id='bash',
        bash_command='pwd',
    )

    ssh_hook = SSHHook(
        ssh_conn_id='uow_gateway',
    )

    t2 = SSHOperator(
        task_id='ssh',
        ssh_hook=ssh_hook,
        command='echo `date` > date.txt',
    )

    # t4 = EmailOperator(
    #     task_id='email',
    #     to=['huchen.zhao@convertlab.com'],
    #     subject='Subject - {{ ds }}',
    #     html_content='Here is the new email',
    #     # files=[local_file],
    # )

    t5 = PythonOperator(
        task_id='http_python',
        python_callable=http_test(),
    )

    t1 >> t2 >> t5
