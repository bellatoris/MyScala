/**
  * Created by DoogieMin on 2017. 2. 15..
  */
class Consolidator(observed: List[BankAccount]) extends Subscriber {
  observed.foreach(_.subscribe(this))

  private var total: Int = _    // uninitialized
  compute()                     // initialize total

  private def compute() =
    total = observed.map(_.currentBalance).sum

  def handler(pub: Publisher): Unit = compute()

  def totalBalance: Int = total
}
