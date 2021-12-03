import unittest

from day01 import Captcha


class day0101Tests(unittest.TestCase):
    captcha = Captcha()

    def setUp(self):
        super().setUp()
        self.captcha.day = 1
        self.captcha.test = 1

    def test_sunshine(self):
        self.assertEqual(self.captcha.process("1122"), 3)

    def test_all_same(self):
        self.assertEqual(self.captcha.process("1111"), 4)

    def test_all_different(self):
        self.assertEqual(self.captcha.process("1234"), 0)

    def test_wrap_around(self):
        self.assertEqual(self.captcha.process("91212129"), 9)


class day0102Tests(unittest.TestCase):
    captcha = Captcha()

    def setUp(self):
        super().setUp()
        self.captcha.day = 1
        self.captcha.test = 2

    def test_sunshine(self):
        self.assertEqual(self.captcha.process("123425"), 4)
        self.assertEqual(self.captcha.process("12131415"), 4)

    def test_all_same(self):
        self.assertEqual(self.captcha.process("123123"), 12)
        self.assertEqual(self.captcha.process("1212"), 6)

    def test_all_different(self):
        self.assertEqual(self.captcha.process("1221"), 0)
