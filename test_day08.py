import unittest

from day08 import Register


class Tests(unittest.TestCase):
    def test_part_1(self):
        testObject = Register()

        sample_input = ["b inc 5 if a > 1",
                        "a inc 1 if b < 5",
                        "c dec -10 if a >= 1",
                        "c inc -20 if c == 10"]
        sample_result = 1
        testObject.test = 1

        self.assertEqual(sample_result, testObject.process(sample_input))

    def test_part_2(self):
        testObject = Register()

        sample_input = ["b inc 5 if a > 1",
                        "a inc 1 if b < 5",
                        "c dec -10 if a >= 1",
                        "c inc -20 if c == 10"]
        sample_result = 10
        testObject.test = 2

        self.assertEqual(sample_result, testObject.process(sample_input))
