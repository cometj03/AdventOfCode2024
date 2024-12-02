use std::usize;

fn main() {
    let input = include_str!("Day01.txt");
    println!("{}", part1(input));
    println!("{}", part2(input));
}

fn part1(input: &str) -> i32 {
    let mut left: Vec<i32> = Vec::new(); 
    let mut right: Vec<i32> = Vec::new();

    for s in input.split("\n") {
        let s: Vec<&str> = s.split_whitespace().collect();
        left.push(s[0].parse().unwrap());
        right.push(s[1].parse().unwrap());
    }

    left.sort();
    right.sort();

    let mut result = 0;
    for i in 0..left.len() {
        result += (left[i] - right[i]).abs();
    }
    result
}

fn part2(input: &str) -> i32 {
    let mut count = [0; 101010];
    let mut left: Vec<i32> = Vec::new();

    for s in input.split("\n") {
        let s: Vec<&str> = s.split_whitespace().collect();
        let (l, r): (i32, i32) = (s[0].parse().unwrap(), s[1].parse().unwrap());
        left.push(l);
        count[r as usize] += 1;
    }

    let mut result = 0;
    for i in left {
        result += i * count[i as usize];
    }
    result
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_part1() {
        let testcase = "3  4\n4  3\n2  5\n1  3\n3  9\n3  3";
        let result = part1(testcase);
        assert_eq!(result, 11);
    }

    #[test]
    fn test_part2() {
        let testcase = "3  4\n4  3\n2  5\n1  3\n3  9\n3  3";
        let result = part2(testcase);
        assert_eq!(result, 31);
    }
}