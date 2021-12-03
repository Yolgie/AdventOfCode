import unittest

from day07 import Circus


class part01Tests(unittest.TestCase):
    circus = Circus()

    def setUp(self):
        super().setUp()
        self.circus.test = 1

    def test_sample(self):
        sample_input = ["pbga (66)",
                        "xhth (57)",
                        "ebii (61)",
                        "havc (66)",
                        "ktlj (57)",
                        "fwft (72) -> ktlj, cntj, xhth",
                        "qoyq (66)",
                        "padx (45) -> pbga, havc, qoyq",
                        "tknk (41) -> ugml, padx, fwft",
                        "jptl (61)",
                        "ugml (68) -> gyxo, ebii, jptl",
                        "gyxo (61)",
                        "cntj (57)"]
        self.assertEqual("tknk", self.circus.process(sample_input))


class part02Tests(unittest.TestCase):
    circus = Circus()

    def setUp(self):
        super().setUp()
        self.circus.test = 2

    def test_sample(self):
        sample_input = ["pbga (66)",
                        "xhth (57)",
                        "ebii (61)",
                        "havc (66)",
                        "ktlj (57)",
                        "fwft (72) -> ktlj, cntj, xhth",
                        "qoyq (66)",
                        "padx (45) -> pbga, havc, qoyq",
                        "tknk (41) -> ugml, padx, fwft",
                        "jptl (61)",
                        "ugml (68) -> gyxo, ebii, jptl",
                        "gyxo (61)",
                        "cntj (57)"]
        self.assertEqual(60, self.circus.process(sample_input))
