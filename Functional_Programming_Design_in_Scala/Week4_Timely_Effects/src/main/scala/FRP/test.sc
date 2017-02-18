import FRP._

val s = Signal(3)
val v = Var(3)
val c = Signal(s() * v())
c()
v() = 4
c()
Signal.caller.values.length
c()
c()
c()
c()
Signal.caller.values.length
