from functools import reduce
from multiprocessing import Pool


class Generator:
    divisor = 2147483647

    def __init__(self, seed, factor, filter_divisor) -> None:
        super().__init__()
        self.previous = seed
        self.factor = factor
        self.filter_divisor = filter_divisor

    def get_next(self):
        while True:
            self.previous = (self.previous * self.factor) % self.divisor
            if self.previous % self.filter_divisor == 0:
                return self.previous

def get_next(generator):
    return generator.get_next()

class Generators:
    day = 15
    test = 2

    def process(self, generator1_seed, generator2_seed):
        if self.test == 1:
            count = 40000000
            generators = [Generator(generator1_seed, 16807, 1),
                          Generator(generator2_seed, 48271, 1)]
        else:
            count = 5000000
            generators = [Generator(generator1_seed, 16807, 4),
                          Generator(generator2_seed, 48271, 8)]

        pool = Pool(2)
        sum = 0
        for i in range(count):
            results = None
            results = list(map(get_next, generators))

            if reduce(lambda a, b: self.sameBinaryTail(a, b), results):
                print(i, results)
                sum += 1

            # if i % 100000 == 0:
            #     print(i)

        print({'count': sum})

    def sameBinaryTail(self, next1, next2):
        return self.getBinaryTail(next1) == self.getBinaryTail(next2)

    def getBinaryTail(self, number):
        binaryTail = format(number, 'b').zfill(32)[-16:]
        return binaryTail


if __name__ == "__main__":
    exercise = Generators()
    exercise.process(65, 8921)
    exercise.process(679, 771)
