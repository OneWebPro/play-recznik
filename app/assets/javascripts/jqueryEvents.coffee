define ["jquery-1.9.0.min"], ($ = jQuery) ->
  $(document).on "keypress", "#polish-translate", (event)->
    code = $.getCode(event)
    if(code == 40 and $('#polish-hints ul li').length != 0)
      if !$('#polish-hints ul li:first button').is(":focus")
        $('#polish-hints ul li:first button').focus()

  $(document).on "keypress", "#serbian-translate", (event) ->
    code = $.getCode(event)
    if(code == 40 and $('#serbian-hints ul li').length != 0)
      $('#serbian-hints ul li:first button').focus()

  $(document).on "keypress", "#serbian-hints ul li button", (event) ->
    $.movinInList('#serbian-hints', event)
    code = $.getCode(event)
    if(code == 40 or code == 38)
      event.preventDefault
      return false

  $(document).on "keypress", "#polish-hints ul li button", (event) ->
    $.movinInList('#polish-hints', event)
    code = $.getCode(event)
    if(code == 40 or code == 38)
      event.preventDefault
      return false

  $.movinInList = (id, event) ->
    code = $.getCode(event)
    if(code == 40 or code == 38 and $(id + ' ul li').length != 0)
      if(code == 40)
        element = $(id + ' ul').find("button:focus").parent().parent().next('li').find('button')
        if(element.length > 0)
          element.focus()
          if !element.isOnScreen()
            $('html, body').animate({
              scrollTop: element.offset().top
            }, 10);
        else
          element = $(id + ' ul li:first').find("button")
          element.focus()
          if !element.isOnScreen()
            $('html, body').animate({
              scrollTop: element.offset().top
            }, 10);
      if(code == 38)
        element = $(id + ' ul').find("button:focus").parent().parent().prev('li').find('button')
        if(element.length > 0)
          element.focus()
          if !element.isOnScreen()
            $('html, body').animate({
              scrollTop: element.offset().top
            }, 10);
        else
          element = $(id + ' ul').find("button:focus").parent().parent().parent().last().find('button')
          element.focus()
          if !element.isOnScreen()
            $('html, body').animate({
              scrollTop: element.offset().top
            }, 10);


  $.getCode = (event) ->
    if event.keyCode?
      code = event.keyCode
    else
      code = event.which
    code

  $.fn.isOnScreen = ->
    win = $(window)
    viewport =
      top: win.scrollTop() + 20
      left: win.scrollLeft()

    viewport.right = viewport.left + win.width()
    viewport.bottom = viewport.top + win.height()
    bounds = @offset()
    bounds.right = bounds.left + @outerWidth()
    bounds.bottom = bounds.top + @outerHeight()
    not (viewport.right < bounds.left or viewport.left > bounds.right or viewport.bottom < bounds.top or viewport.top > bounds.bottom)

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

  offset = 220;
  duration = 500;

  $(window).scroll ->
    if $(@).scrollTop() > offset
      $('.back-to-top').fadeIn duration
    else
      $('.back-to-top').fadeOut duration


  $('.back-to-top').click (event) ->
    event.preventDefault()
    $('html, body').animate({scrollTop: 0}, duration)
    false