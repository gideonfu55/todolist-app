
// Function to handle checkbox click events to handleItemComplete POST method in TodoItemController:
function handleCheckboxClick(event) {
  const form = event.target.closest("form");
  form.submit();
}

