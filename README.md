# Orbit MVI notes
Follow guide here: https://orbit-mvi.org/ to get started.

## Essentials
* Define a contract specifying state & side-effects. State should be parcelable to support process death, and you need to pass in a SavedStateHandle to the Container.
* Implement ContainerHost, typically in your ViewModels
* Observe either the entire viewmodel or independent flows (for state + side-effects)
* Don't emit actions--just call methods on VM directly, then use `intent{}` 

## Implementation notes
* Guidance on defining the contract could be better: https://orbit-mvi.org/#define-the-contract
* Is it OK to just `postSideEffect{}` without `reduce{}` ? YES
* Can multiple sideEffects or `reduce{}` calls be made within an `intent{}` ? Seems to work, but is that normal? What about conditionally calling reduce?