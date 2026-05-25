# CategoryController 接口测试

pytest + requests 对 CategoryController 5 个接口的 HTTP 集成测试，共 16 个用例。

## 前置

```bash
pip3 install pytest pytest-sugar requests
```

## 运行

```bash
# 基础
python3 -m pytest test_category.py -v

# 彩色美化（二选一）
python3 -m pytest test_category.py -v               # pytest-sugar 自动生效
python3 -m pytest test_category.py -v --rich        # pytest-rich 风格
```
