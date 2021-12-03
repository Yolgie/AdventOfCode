import unittest

from day10 import Hash


class Tests(unittest.TestCase):
    def test_part_1(self):
        testObject = Hash()
        testObject.test = 1
        testObject.list_size = 5

        self.assertEqual(12, testObject.process(["3, 4, 1, 5"]))
