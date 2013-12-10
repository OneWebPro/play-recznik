package bootstrap

import views.html.helper.FieldConstructor

/**
 * @author loki
 */

object Forms {
	implicit val horizontalInput = FieldConstructor(views.html.forms.horizontalInput.f)
	implicit val verticalInput = FieldConstructor(views.html.forms.verticalInput.f)

	implicit val horizontalRadio = FieldConstructor(views.html.forms.horizontalRadio.f)
	implicit val verticalRadio = FieldConstructor(views.html.forms.verticalRadio.f)

	implicit val horizontalCheckbox = FieldConstructor(views.html.forms.horizontalCheckbox.f)
	implicit val verticalCheckbox = FieldConstructor(views.html.forms.verticalCheckbox.f)

	implicit val horizontalSelect = FieldConstructor(views.html.forms.horizontalSelect.f)
	implicit val verticalSelect = FieldConstructor(views.html.forms.verticalSelect.f)

}

