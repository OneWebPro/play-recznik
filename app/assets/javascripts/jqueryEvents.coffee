define ["jquery-1.9.0.min"], ($ = jQuery) ->
  $("body").on "click", ".letters .btn", (event) ->
    event.stopPropagation()
    $this = $(event.target)
    input = $(".keybord-focus")
    input.val(input.val() + $this.text())
    input.trigger('input')
    input.trigger('focus')
    false

  $("body").on "focus", "input,textarea", (event)->
    $this = $(event.target)
    $(".keybord-focus").removeClass("keybord-focus")
    $this.addClass("keybord-focus")