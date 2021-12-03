import unittest

from day11 import Hex


class Tests(unittest.TestCase):
    def test_part_1(self):
        testObject = Hex()
        testObject.test = 1

        self.assertEqual(3, testObject.process(["ne,ne,ne"]))
        self.assertEqual(0, testObject.process(["ne,ne,sw,sw"]))
        self.assertEqual(2, testObject.process(["ne,ne,s,s"]))
        self.assertEqual(3, testObject.process(["ne,ne,ne"]))
