/**
  * Created by DoogieMin on 2017. 2. 15..
  */
trait Subscriber {
  def handler(pub: Publisher)
}
