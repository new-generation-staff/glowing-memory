#python

#安装虚拟环境 venv
# https://zhuanlan.zhihu.com/p/392850413
```markdown
python 各个依赖之间会影响，所以相同依赖的项目用一个虚拟环境 (venv)
python3 -m venv .venv
source .venv/bin/activate
pip install pycryptodome
pip list
之后用idea打开就可以使用这个虚拟环境中的依赖 (或者在idea的project structure中把python sdk指向虚拟环境中的python)
```

```markdown
#更新pip命令
pip install  --upgrade pip
```

```markdown
#安装airflow
pip3 install apache-airflow
pip3 install apache-airflow-providers-ssh
```

```markdown
#退出虚拟环境
deactivate
```