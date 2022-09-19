package bank.account.adapters;

public interface IModelToDTOAdapter<S, T> {
    S modelToDTO(T model);
}
