def failure_callback(context: dict):
    dag_id = context['dag'].dag_id
    email = context['dag'].default_args['email']
    phone = context['dag'].default_args['phone']
    schedule_interval = context['dag'].schedule_interval
    task_id = context['task_instance'].task_id
    run_id = context['run_id']
    operator = context['task_instance'].operator
    state = context['task_instance'].state
    duration = '%.1f' % context['task_instance'].duration
    max_tries = context['task_instance'].max_tries
    hostname = context['task_instance'].hostname
    start_date = context['task_instance'].start_date.strftime('%Y-%m-%d %H:%M:%S')
    end_date = context['task_instance'].end_date.strftime('%Y-%m-%d %H:%M:%S')
    params = context['params']
    var = context['var']
    test_mode = context['test_mode']
    exception = context['exception']
    execution_date = context['execution_date'].strftime('%Y-%m-%d %H:%M:%S')
    prev_execution_date = context['prev_execution_date'].strftime('%Y-%m-%d %H:%M:%S')
    next_execution_date = context['next_execution_date'].strftime('%Y-%m-%d %H:%M:%S')

    msg = f"""<table width='100%' border='1' cellpadding='2' style='border-collapse: collapse'>
        <h1 style='color: red;'>Airflow {task_id}任务报警</h1>
        <tr><td width='150px'>DAG名称</td><td>{dag_id}</td></tr>
        <tr><td width='150px'>任务名称</td><td>{task_id}</td></tr>
        <tr><td width='150px'>运行周期</td><td>{schedule_interval}</td></tr>
        <tr><td width='150px'>运行 ID</td><td>{run_id}</td></tr>
        <tr><td width='150px'>任务类型</td><td>{operator}</td></tr>
        <tr><td width='150px' style='color: red;'>任务状态</td><td style='color: red;'>{state}</td></tr>
        <tr><td width='150px'>重试次数</td><td>{max_tries}</td></tr>
        <tr><td width='150px'>持续时长</td><td>{duration}s</td></tr>
        <tr><td width='150px'>运行主机</td><td>{hostname}</td></tr>
        <tr><td width='150px'>计划执行时间</td><td>{execution_date}</td></tr>
        <tr><td width='150px'>实际开始时间</td><td>{start_date}</td></tr>
        <tr><td width='150px'>实际结束时间</td><td>{end_date}</td></tr>
        <tr><td width='150px'>上次执行时间</td><td>{prev_execution_date}</td></tr>
        <tr><td width='150px'>下次执行时间</td><td>{next_execution_date}</td></tr>
        <tr><td width='150px'>参数</td><td>{params}</td></tr>
        <tr><td width='150px'>变量</td><td>{var}</td></tr>
        <tr><td width='150px'>是否测试模式</td><td>{test_mode}</td></tr>
        <tr><td width='150px' style='color: red;'>错误信息</td><td style='color: red;'>{exception}</td></tr></table>
    """

    send_email = ','.join(email)
    subject_msg = f'Airflow {task_id}任务报警'
    # send_email_fun(msg, send_email, subject_msg)
    # if phone is not None and len(phone) > 0:
    #     send_sms = f'Airflow DAG是【{dag_id}】的【{task_id}】任务异常'
    #     send_phone = ','.join(phone)
    #     send_sms_fun(send_sms, send_phone)