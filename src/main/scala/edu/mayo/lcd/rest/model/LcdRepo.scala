package edu.mayo.lcd.rest.model


trait LcdRepo[T] {

  def getAll: Option[List[T]]

  def get(id: Any): Option[T]

  def create(resource: T): Option[T]

  def update(resource: T): Option[T]

  def delete(resource: T): (Boolean, Option[String])
}
