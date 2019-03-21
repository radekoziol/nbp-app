'use strict';

// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');
// end::vars[]


// tag::app[]
class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {users: []};
    }

    componentDidMount() {
        client({method: 'GET', path: root + '/users'}).done(response => {
            this.setState({users: response.entity});
        });
    }

    render() {
        return (
            <UserList users={this.state.users}/>
        )
    }
}

// end::app[]

// tag::user-list[]
class UserList extends React.Component {
    render() {

        const users = this.props.users.map(user =>
            <User key={user.username} user={user}/>);

        console.log(users);

        return (
            <table>
                <tbody>
                <tr>
                    <th>Username</th>
                    <th>Password</th>
                    <th>Email</th>
                </tr>
                {users}
                </tbody>
            </table>
        )
    }
}

// end::user-list[]

// tag::users[]
class User extends React.Component {
    render() {
        return (
            <tr>
                <td>{this.props.user.username}</td>
                <td>{this.props.user.password}</td>
                <td>{this.props.user.email}</td>
            </tr>
        )

    }
}

// end::users[]

// tag::render[]
ReactDOM.render(
    <App/>,
    document.getElementById('react')
);
// end::render[]