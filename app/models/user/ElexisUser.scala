package models.user

case class ElexisUser(
  id: String,
  permissions: Seq[String],
  roles: Seq[String],
  name: String,
  token: String
) extends User
