type Set = Int => Boolean
def contains(s: Set, elem: Int): Boolean = s(elem)

def singletonSet(elem: Int): Set = {
  x: Int => Boolean
    if (x == elem) true else false
}

def union(s: Set, t: Set): Set = {
  x: Int => Boolean
    if (contains(s, x) || contains(t, x)) true
    else false
}

def intersect(s: Set, t: Set): Set = {
  x: Int => Boolean
    if (contains(s, x) && contains(t, x)) true
    else false
}

def diff(s: Set, t: Set): Set = {
  x: Int => Boolean
    if (contains(s, x) && !contains(t, x)) true
    else false
}

def filter(s: Set, p: Int => Boolean): Set = {
  x: Int => Boolean
    if (contains(s, x) && p(x)) true
    else false
}