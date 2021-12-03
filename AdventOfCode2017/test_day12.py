import unittest

from day12 import Pipes


class Tests(unittest.TestCase):
    def test_part_1(self):
        testObject = Pipes()
        testObject.test = 1

        sample_input = ["0 <-> 2",
                        "1 <-> 1",
                        "2 <-> 0, 3, 4",
                        "3 <-> 2, 4",
                        "4 <-> 2, 3, 6",
                        "5 <-> 6",
                        "6 <-> 4, 5"]

        self.assertEqual(6, testObject.process(sample_input))
