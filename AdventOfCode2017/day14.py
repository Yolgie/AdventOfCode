from day10 import Hash

class Defragment:
    day = 14
    test = 1

    def process(self, raw_input):
        hash = Hash()
        knots = [hash.process([raw_input+'-'+str(i)]) for i in range(128)]
        binary = [''.join([format(knot_part, 'b').zfill(8) for knot_part in knot['denseHash']]) for knot in knots]
        used_count = sum([sum([int(binary_pos) for binary_pos in binary_part]) for binary_part in binary])
        field = [list(map(lambda i: i=='1', binary_part)) for binary_part in binary]

        group_count = 0
        for x in range(128):
            for y in range(128):
                if (field[x][y]):
                    group_count += 1
                    self.remove_group(field, x, y)

        print({'ones': used_count, 'group_count': group_count})

    def remove_group(self, field, x, y):
        if 0 <= x < 128 and 0 <= y < 128 and field[x][y]:
            field[x][y] = False
            self.remove_group(field, x+1, y)
            self.remove_group(field, x, y+1)
            self.remove_group(field, x-1, y)
            self.remove_group(field, x, y-1)




if __name__ == "__main__":
    exercise = Defragment()
    exercise.process("flqrgnkx")
    exercise.process("nbysizxe")