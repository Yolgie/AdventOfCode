import unittest

from day06 import Memory, Memories


class part01Tests(unittest.TestCase):
    memories = Memories()

    def setUp(self):
        super().setUp()
        self.memories.test = 1

    def test_sample(self):
        self.assertEqual(self.memories.process(["0 2 7 0"]), 5)

    def test_memory(self):
        memory = Memory([0, 2, 7, 0])
        self.assertFalse(memory.execute())
        self.assertEqual(memory.currentState, [2, 4, 1, 2])
        self.assertFalse(memory.execute())
        self.assertEqual(memory.currentState, [3, 1, 2, 3])
        self.assertFalse(memory.execute())
        self.assertEqual(memory.currentState, [0, 2, 3, 4])
        self.assertFalse(memory.execute())
        self.assertEqual(memory.currentState, [1, 3, 4, 1])
        self.assertTrue(memory.execute())
        self.assertEqual(memory.currentState, [2, 4, 1, 2])


class part02Tests(unittest.TestCase):
    memories = Memories()

    def setUp(self):
        super().setUp()
        self.memories.test = 2

    def test_sample(self):
        self.assertEqual(self.memories.process(["0 2 7 0"]), 4)
