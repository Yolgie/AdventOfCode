import unittest

from day09 import Stream


class Tests(unittest.TestCase):
    def test_part_1_count(self):
        testObject = Stream()
        testObject.test = 1

        self.assertEqual(1, testObject.process(["{}"])['count'])
        self.assertEqual(3, testObject.process(["{{{}}}"])['count'])
        self.assertEqual(3, testObject.process(["{{},{}}"])['count'])
        self.assertEqual(6, testObject.process(["{{{},{},{{}}}}"])['count'])
        self.assertEqual(1, testObject.process(["{<{},{},{{}}>}"])['count'])
        self.assertEqual(1, testObject.process(["{<a>,<a>,<a>,<a>}"])['count'])
        self.assertEqual(5, testObject.process(["{{<a>},{<a>},{<a>},{<a>}}"])['count'])
        self.assertEqual(2, testObject.process(["{{<!>},{<!>},{<!>},{<a>}}"])['count'])

    def test_part_1_score(self):
        testObject = Stream()
        testObject.test = 1

        self.assertEqual(1, testObject.process(["{}"])['score'])
        self.assertEqual(6, testObject.process(["{{{}}}"])['score'])
        self.assertEqual(5, testObject.process(["{{},{}}"])['score'])
        self.assertEqual(16, testObject.process(["{{{},{},{{}}}}"])['score'])
        self.assertEqual(1, testObject.process(["{<{},{},{{}}>}"])['score'])
        self.assertEqual(1, testObject.process(["{<a>,<a>,<a>,<a>}"])['score'])
        self.assertEqual(9, testObject.process(["{{<a>},{<a>},{<a>},{<a>}}"])['score'])
        self.assertEqual(3, testObject.process(["{{<!>},{<!>},{<!>},{<a>}}"])['score'])

    def test_part_2_garbage(self):
        testObject = Stream()
        testObject.test = 1

        self.assertEqual(0, testObject.process(["{}"])['garbage'])
        self.assertEqual(0, testObject.process(["{<>}"])['garbage'])
        self.assertEqual(17, testObject.process(["{<random characters>}"])['garbage'])
        self.assertEqual(3, testObject.process(["{<<<<>}"])['garbage'])
        self.assertEqual(2, testObject.process(["{<{!>}>}"])['garbage'])
        self.assertEqual(0, testObject.process(["{<!!>}"])['garbage'])
        self.assertEqual(0, testObject.process(["{<!!!>>}"])['garbage'])
        self.assertEqual(10, testObject.process(["{<{o\"i!a,<{i<a>}"])['garbage'])
