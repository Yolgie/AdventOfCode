import unittest

from day04 import Passphrases


class part01Tests(unittest.TestCase):
    passphrases = Passphrases()

    def setUp(self):
        super().setUp()
        self.passphrases.test = 1

    def test_sample(self):
        self.assertEqual(self.passphrases.process([["1", "1", "2"]]), 0)
        self.assertEqual(self.passphrases.process([["1", "1", "2"], ["1", "3", "2"], ["1", "4", "2"]]), 2)
