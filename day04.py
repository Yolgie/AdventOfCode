class Passphrases:
    day = 4
    test = 2

    def process(self, raw_input):
        passphrases = self.parseInput(raw_input)
        validPasshrases = self.filterValidPassphrases(passphrases)
        result = len(validPasshrases)
        return result

    def filterValidPassphrases(self, passphrases):
        return [passphrase for passphrase in passphrases if self.isValidPassphrase(passphrase)]

    def isValidPassphrase(self, passphrase):
        if self.test == 1:
            return len(passphrase) == len(set(passphrase))
        if self.test == 2:
            return len(passphrase) == len(set([''.join(sorted(password)) for password in passphrase]))

    def parseInput(self, raw_input):
        return [row.split() for row in raw_input]

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
    spiralMemory = Passphrases()
    spiralMemory.executeTestOnFile(spiralMemory.get_input_filename())
