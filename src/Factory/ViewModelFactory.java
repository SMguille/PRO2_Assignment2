package Factory;

import Network.NetworkClient;
import ViewModel.CreateVinylViewModel;
import ViewModel.VinylListViewModel;
import Factory.ModelFactory;

public class ViewModelFactory {
  private final NetworkClient network;

  private VinylListViewModel vinylListVM;
  private CreateVinylViewModel createVinylVM;

  public ViewModelFactory(NetworkClient network) {
    this.network = network;
  }

  public VinylListViewModel getVinylListViewModel() {
    if (vinylListVM == null) {
      vinylListVM = new VinylListViewModel(network);
      network.setOnFullListReceived(vinylListVM::onServerFullList);
    }
    return vinylListVM;
  }

  public CreateVinylViewModel getCreateVinylViewModel() {
    if (createVinylVM == null) {
      createVinylVM = new CreateVinylViewModel(network);
    }
    return createVinylVM;
  }
}
