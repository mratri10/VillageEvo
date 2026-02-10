# Backlog

## Refactoring / Code Quality

- [ ] **Hardcoded NPC Initialization**: In `NpcViewModel.saveNpcFirst`, the NPC creation loop is hardcoded (`1..5`). Refactor this to be more dynamic or part of a proper "New Game" initialization flow.
- [ ] **Magic Strings in SQL**: `MapUserDao` uses hardcoded strings like `'forest'`, `'wild'`, `'farm'` in SQL queries. Move these to a `const` object or Enum to prevent typos and improve maintainability.
- [ ] **Error Handling in ViewModels**:
    - `MapViewModel.turnProcess`: Currently just prints the stack trace on failure. Implement proper error logging (e.g., Timber) and UI feedback (Sanckbar/Toast) for the user.
    - `MapViewModel.dataMapUserById`: Has a comment `// Opsional: Log atau set ke meta kosong`. Implement a fallback mechanism or proper error state when metadata is missing.

## Features & Improvements

- [ ] **Dynamic Map Generation**: Ensure map generation is robust and not just relying on hardcoded initial data.
- [ ] **NPC Assignment Validation**: Add checks to prevent assigning NPCs to invalid map tiles or exceeding tile capacity.
- [ ] **Turn Logic Optimization**: The `turnProcess` logic interacts heavily with the DB. Monitor performance as the number of entities grows and consider batch optimizations if needed.

## Testing

- [ ] **Unit Tests**: Add unit tests for `turnProcess` in `MapViewModel` to ensure resource calculations are correct.
- [ ] **DAO Tests**: Add instrumented tests for `NpcDao` complex queries to verify filtering and sorting logic.
