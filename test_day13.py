import unittest

from day13 import Firewall


class Tests(unittest.TestCase):
    def test_part_1(self):
        testObject = Firewall()
        testObject.test = 1

        sample_input = ["0: 3",
                        "1: 2",
                        "4: 4",
                        "6: 4"]

        self.assertEqual(24, testObject.process(sample_input)['severityOn0Start'])
        self.assertEqual(10, testObject.process(sample_input)['delay'])
