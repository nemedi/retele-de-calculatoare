using System.ServiceModel;

namespace Common
{
	[ServiceContract]
	public interface IChatCallback
	{
		[OperationContract(IsOneWay = true)]
		void OnAccept(string[] names);

		[OperationContract(IsOneWay = true)]
		void OnDeny();

		[OperationContract(IsOneWay = true)]
		void OnAddUser(string name);

		[OperationContract(IsOneWay = true)]
		void OnRemoveUser(string name);

		[OperationContract(IsOneWay = true)]
		void OnReceive(string from, string message);

		[OperationContract(IsOneWay = true)]
		void OnExit();
	}
}
