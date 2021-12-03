import unittest

from day03 import SpiralMemory, Grid, calculateSpiralPosition


class part01Tests(unittest.TestCase):
    spiralMemory = SpiralMemory()

    def setUp(self):
        super().setUp()
        self.spiralMemory.test = 1

    def test_sample(self):
        self.assertEqual(self.spiralMemory.process(["1"]), 0)
        self.assertEqual(self.spiralMemory.process(["12"]), 3)
        self.assertEqual(self.spiralMemory.process(["23"]), 2)
        self.assertEqual(self.spiralMemory.process(["1024"]), 31)


class part02(unittest.TestCase):
    spiralMemory = SpiralMemory()

    def setUp(self):
        super().setUp()
        self.spiralMemory.test = 2

    def test_gird(self):
        grid = Grid()
        self.assertEqual(grid.calculateNext(), 1)
        self.assertEqual(grid.calculateNext(), 2)
        self.assertEqual(grid.calculateNext(), 4)
        self.assertEqual(grid.calculateNext(), 5)
        self.assertEqual(grid.calculateNext(), 10)
        self.assertEqual(grid.calculateNext(), 11)
        self.assertEqual(grid.calculateNext(), 23)
        self.assertEqual(grid.calculateNext(), 25)
        self.assertEqual(grid.calculateNext(), 26)
        self.assertEqual(grid.calculateNext(), 54)
        self.assertEqual(grid.calculateNext(), 57)

    def test_calculate_spiral_position(self):
        self.assertEqual(calculateSpiralPosition(1), (0, 0))
        self.assertEqual(calculateSpiralPosition(2), (1, 0))
        self.assertEqual(calculateSpiralPosition(3), (1, 1))
        self.assertEqual(calculateSpiralPosition(4), (0, 1))
        self.assertEqual(calculateSpiralPosition(5), (-1, 1))
        self.assertEqual(calculateSpiralPosition(6), (-1, 0))

    def test_first_greater(self):
        self.assertEqual(self.spiralMemory.process(["0"]), 1)
        self.assertEqual(self.spiralMemory.process(["1"]), 2)
        self.assertEqual(self.spiralMemory.process(["2"]), 4)
        self.assertEqual(self.spiralMemory.process(["3"]), 4)
        self.assertEqual(self.spiralMemory.process(["4"]), 5)
        self.assertEqual(self.spiralMemory.process(["5"]), 10)
        self.assertEqual(self.spiralMemory.process(["6"]), 10)
        self.assertEqual(self.spiralMemory.process(["7"]), 10)
        self.assertEqual(self.spiralMemory.process(["8"]), 10)
        self.assertEqual(self.spiralMemory.process(["10"]), 11)
        self.assertEqual(self.spiralMemory.process(["400"]), 747)
        self.assertEqual(self.spiralMemory.process(["800"]), 806)
