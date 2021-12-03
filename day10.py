from functools import reduce
from itertools import cycle, islice


class Hash:
    day = 10
    test = 2
    list_size = 256

    def process(self, raw_input):
        lenghts = self.parseInput(raw_input)
        problem = list(range(self.list_size))
        if self.test == 1:
            rounds = 1
        else:
            rounds = 64
        skip_size = 0
        pos = 0
        for _ in range(rounds):
            for lenght in lenghts:
                part = list(islice(cycle(problem), pos, pos + lenght))[::-1]
                self.listReplace(problem, part, pos)
                pos = (pos + lenght + skip_size) % self.list_size
                skip_size += 1
                # print(problem)

        if self.test == 1:
            return problem[0] * problem[1]
        else:
            chunk_size = 16
            dense_hash_groups = [problem[i:i + chunk_size] for i in range(0, len(problem), chunk_size)]
            dense_hash = [reduce(lambda x, y: x ^ y, hash_group) for hash_group in dense_hash_groups]
            hex_hash = ["%02X" % val for val in dense_hash]
            return {'hexHash': ''.join(hex_hash), 'denseHash': dense_hash}

    def listReplace(self, original, new, start):
        if len(new) + start > len(original):
            new_size = len(original) - start
            original[start:] = new[:new_size]
            return self.listReplace(original, new[new_size:], 0)
        original[start:start + len(new)] = new
        return original

    def parseInput(self, raw_input):
        if self.test == 1:
            return [[int(value) for value in row.split(',')] for row in raw_input][0]
        if self.test == 2:
            parsed_input = [[ord(value) for value in row] for row in raw_input][0]
            parsed_input.extend([17, 31, 73, 47, 23])
            return parsed_input

    def executeTestOnFile(self, input_filename):
        with open(input_filename) as input_file:
            raw_input = input_file.readlines()

        result = self.process(raw_input)
        print(result)

        with open(self.get_output_filename(), 'w') as output_file:
            output_file.write(str(result))

    def get_output_filename(self):
        return "day" + str(self.day).zfill(2) + "." + str(self.test).zfill(2) + ".output"

    def get_input_filename(self):
        return "day" + str(self.day).zfill(2) + ".input"


if __name__ == "__main__":
    exercise = Hash()
    exercise.executeTestOnFile(exercise.get_input_filename())
