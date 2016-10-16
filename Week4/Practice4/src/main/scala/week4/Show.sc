import week4._

Sum(Prod(Number(2), Var("x")), Var("y")).show
Prod(Sum(Number(2), Var("x")), Var("y")).show
Prod(Sum(Number(2), Var("x")), Sum(Number(2), Var("x"))).show
Prod(Number(3), Sum(Number(2), Var("x"))).show


