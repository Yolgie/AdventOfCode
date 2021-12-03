def get_output_filename(input_filename):
    return input_filename.replace('input', 'output')


class Captcha:
    day = 1
    test = 2

    def getDistance(self, input_lengt):
        if self.day == 1 and self.test == 1:
            return 1
        if self.day == 1 and self.test == 2:
            return int(input_lengt / 2)

    def get_input_filename(self):
        return "day" + str(self.day).zfill(2) + ".input"

    def process(self, raw_input):
        input_list = self.parseInput(raw_input)
        distance = self.getDistance(len(input_list))
        result = self.calculate_sum_of_digits(input_list, distance)
        return result

    def calculate_sum_of_digits(self, input_list, distance):
        sum = 0
        for pos, current in enumerate(input_list):
            if current == self.get_distant_digit(input_list, pos, distance):
                sum += current
            last = current
        return sum

    @staticmethod
    def get_distant_digit(intput_list, pos, distance):
        otherPos = (pos + distance) % (len(intput_list))
        return intput_list[otherPos]

    def parseInput(self, raw_input):
        return [int(char) for char in list(raw_input)]

    def executeTestOnFile(self, input_filename):
        with open(input_filename) as input_file:
            raw_input = input_file.readlines()[0]

        result = self.process(raw_input)
        print(result)

        with open(get_output_filename(input_filename), 'w') as output_file:
            output_file.write(str(result))


if __name__ == "__main__":
    captcha = Captcha()
    captcha.day = 1
    captcha.test = 2
    captcha.executeTestOnFile(captcha.get_input_filename())
