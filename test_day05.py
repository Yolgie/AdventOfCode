import unittest

from day05 import Jumps


class part01Tests(unittest.TestCase):
    jumps = Jumps()

    def setUp(self):
        super().setUp()
        self.jumps.test = 1

    def test_sample(self):
        self.assertEqual(self.jumps.process(["0", "3", "0", "1", "-3"]), 5)


class part02Tests(unittest.TestCase):
    jumps = Jumps()

    def setUp(self):
        super().setUp()
        self.jumps.test = 2

    def test_sample(self):
        self.assertEqual(self.jumps.process(["0", "3", "0", "1", "-3"]), 10)
