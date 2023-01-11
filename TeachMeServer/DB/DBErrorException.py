from typing import Tuple


class DBErrorException(Exception):
    def __init__(self, message: str):
        super().__init__(message)

    def get_as_response(self) -> Tuple[dict, int]:
        return {"error": str(self)}, 404


# def th(i: int):
#     if i <= 0:
#         raise DBErrorException("NOT GOOD")
#     return i
#
#
# class A:
#     def func(self, a: int, b: int, c: int = 10, ddd: int = 11):
#         return a + b + c + ddd
#
#
# def try_me(f, *args, **kwargs):
#     return f(*args, **kwargs)
#
# a_c = A()
#
# print(try_me(a_c.func))h(i: int):
#     if i <= 0:
#         raise DBErrorException("NOT GOOD")
#     return i
#
#
# class A:
#     def func(self, a: int, b: int, c: int = 10, ddd: int = 11):
#         return a + b + c + ddd
#
#
# def try_me(f, *args, **kwargs):
#     return f(*args, **kwargs)
#
# a_c = A()
#
# print(try_me(a_c.func))

# try:
#     i = th(-1)
# except DBErrorException as ex:
#     s = str(ex)
#     s += " 4"
#     print(s)