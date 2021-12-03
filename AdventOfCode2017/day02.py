import itertools


def get_output_filename(input_filename):
    return input_filename.replace('input', 'output')


class Checksum:
    day = 2
    test = 2

    def get_input_filename(self):
        return "day" + str(self.day).zfill(2) + ".input"

    def process(self, raw_input):
        input_spreadsheet = self.parseInput(raw_input)
        row_checksums = self.calculate_row_checkums(input_spreadsheet)
        result = sum(row_checksums)
        return result

    def calculate_row_checkums(self, input_spreadsheet):
        if self.test == 1:
            return [self.calculate_checksum(row) for row in input_spreadsheet]
        if self.test == 2:
            return [self.get_divisors(row) for row in input_spreadsheet]

    def calculate_checksum(self, row):
        return max(row) - min(row)

    def get_divisors(self, row):
        for a, b in itertools.permutations(row, 2):
            if a % b == 0:
                return int(a / b)

    def parseInput(self, raw_input):
        result = []
        for row in raw_input:
            result.append([int(number) for number in row.split()])
        return result

    def executeTestOnFile(self, input_filename):
        with open(input_filename) as input_file:
            raw_input = input_file.readlines()

        result = self.process(raw_input)
        print(result)

        with open(get_output_filename(input_filename), 'w') as output_file:
            output_file.write(str(result))


if __name__ == "__main__":
    checksum = Checksum()
    checksum.test = 2
    checksum.executeTestOnFile(checksum.get_input_filename())
