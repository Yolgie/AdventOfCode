import unittest

from day02 import Checksum


class day0201Tests(unittest.TestCase):
    checksum = Checksum()

    def setUp(self):
        super().setUp()
        self.checksum.day = 2
        self.checksum.test = 1

    def test_sample(self):
        self.assertEqual(self.checksum.process(["5 1 9 5", "7 5 3", "2 4 6 8"]), 18)


class day0202Tests(unittest.TestCase):
    checksum = Checksum()

    def setUp(self):
        super().setUp()
        self.checksum.day = 2
        self.checksum.test = 2

    def test_sample(self):
        self.assertEqual(self.checksum.process(["5 9 2 8", "9 4 7 3", "3 8 6 5"]), 9)
